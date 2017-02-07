package MyFragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.format.Formatter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.lzy.okhttpserver.download.DownloadInfo;
import com.lzy.okhttpserver.download.DownloadManager;
import com.lzy.okhttpserver.download.DownloadService;
import com.lzy.okhttpserver.listener.DownloadListener;
import com.lzy.okhttpserver.task.ExecutorWithListener;

import java.io.File;
import java.util.List;

import film.ganandrose.R;
import javabean.Films;
import utils.ApkUtils;
import utils.AppCacheUtils;
import utils.NumberProgressBar;

/**
 * Created by p on 2016/11/2.
 */
public class MyFragment_4 extends Fragment implements View.OnClickListener, ExecutorWithListener.OnAllTaskEndListener{

    private List<DownloadInfo> allTask;
    private MyAdapter adapter;
    private DownloadManager downloadManager;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_fragment_4,null);

        downloadManager = DownloadService.getDownloadManager();
        allTask = downloadManager.getAllTask();
        ListView listView = (ListView) view.findViewById(R.id.listView);
        adapter = new MyAdapter();
        listView.setAdapter(adapter);
        downloadManager.getThreadPool().getExecutor().addOnAllTaskEndListener(this);

        return view;
    }
    public void onAllTaskEnd() {
        for (DownloadInfo downloadInfo : allTask) {
            if (downloadInfo.getState() != DownloadManager.FINISH) {
                Toast.makeText(getActivity(), "所有下载线程结束，部分下载未完成", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        Toast.makeText(getActivity(), "所有下载任务完成", Toast.LENGTH_SHORT).show();
    }
    public void onDestroyView() {
        super.onDestroyView();
        //记得移除，否者会回调多次
        downloadManager.getThreadPool().getExecutor().removeOnAllTaskEndListener(this);
    }
    public void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.removeAll:
                downloadManager.removeAllTask();
                adapter.notifyDataSetChanged();  //移除的时候需要调用
                break;
            case R.id.pauseAll:
                downloadManager.pauseAllTask();
                break;
            case R.id.stopAll:
                downloadManager.stopAllTask();
                break;
            case R.id.startAll:
                downloadManager.startAllTask();
                break;
        }
    }
    //给ListView设置适配器
    private class MyAdapter extends BaseAdapter {
        public int getCount() {
            return allTask.size();
        }
        public DownloadInfo getItem(int position) {
            return allTask.get(position);
        }
        public long getItemId(int position) {
            return position;
        }
        public View getView(final int position, View convertView, ViewGroup parent) {
            DownloadInfo downloadInfo = getItem(position);
            ViewHolder holder;
            if (convertView == null) {
                convertView = View.inflate(getActivity(), R.layout.my_fragment_4_listview_item, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.refresh(downloadInfo);

            //对于非进度更新的ui放在这里，对于实时更新的进度ui，放在holder中
            Films film = (Films) AppCacheUtils.getInstance(getActivity()).getObject(downloadInfo.getUrl());
            if (film != null) {
                Glide.with(getActivity()).load(film.getCoverImg()).into(holder.icon);
                holder.name.setText(film.getMovieName());
            } else {
                holder.name.setText(downloadInfo.getFileName());
            }
            holder.download.setOnClickListener(holder);
            holder.remove.setOnClickListener(holder);
            holder.restart.setOnClickListener(holder);

            DownloadListener downloadListener = new MyDownloadListener();
            downloadListener.setUserTag(holder);
            downloadInfo.setListener(downloadListener);
            return convertView;
        }
    }

    private class ViewHolder implements View.OnClickListener {
        private DownloadInfo downloadInfo;
        private ImageView icon;
        private TextView name;
        private TextView downloadSize;
        private TextView tvProgress;
        private TextView netSpeed;
        private NumberProgressBar pbProgress;
        private Button download;
        private Button remove;
        private Button restart;

        public ViewHolder(View convertView) {
            icon = (ImageView) convertView.findViewById(R.id.icon);
            name = (TextView) convertView.findViewById(R.id.name);
            downloadSize = (TextView) convertView.findViewById(R.id.downloadSize);
            tvProgress = (TextView) convertView.findViewById(R.id.tvProgress);
            netSpeed = (TextView) convertView.findViewById(R.id.netSpeed);
            pbProgress = (NumberProgressBar) convertView.findViewById(R.id.pbProgress);
            download = (Button) convertView.findViewById(R.id.start);
            remove = (Button) convertView.findViewById(R.id.remove);
            restart = (Button) convertView.findViewById(R.id.restart);
        }

        public void refresh(DownloadInfo downloadInfo) {
            this.downloadInfo = downloadInfo;
            refresh();
        }

        //对于实时更新的进度ui，放在这里，例如进度的显示，而图片加载等，不要放在这，会不停的重复回调
        //也会导致内存泄漏
        private void refresh() {
            String downloadLength = Formatter.formatFileSize(getActivity(), downloadInfo.getDownloadLength());
            String totalLength = Formatter.formatFileSize(getActivity(), downloadInfo.getTotalLength());
            downloadSize.setText(downloadLength + "/" + totalLength);
            if (downloadInfo.getState() == DownloadManager.NONE) {
                netSpeed.setText("停止");
                download.setText("下载");
            } else if (downloadInfo.getState() == DownloadManager.PAUSE) {
                netSpeed.setText("暂停中");
                download.setText("继续");
            } else if (downloadInfo.getState() == DownloadManager.ERROR) {
                netSpeed.setText("下载出错");
                download.setText("出错");
            } else if (downloadInfo.getState() == DownloadManager.WAITING) {
                netSpeed.setText("等待中");
                download.setText("等待");
            } else if (downloadInfo.getState() == DownloadManager.FINISH) {
                if (ApkUtils.isAvailable(getActivity(), new File(downloadInfo.getTargetPath()))) {
                    download.setText("卸载");
                } else {
                    download.setText("安装");
                }
                netSpeed.setText("下载完成");
            } else if (downloadInfo.getState() == DownloadManager.DOWNLOADING) {
                String networkSpeed = Formatter.formatFileSize(getActivity(), downloadInfo.getNetworkSpeed());
                netSpeed.setText(networkSpeed + "/s");
                download.setText("暂停");
            }
            tvProgress.setText((Math.round(downloadInfo.getProgress() * 10000) * 1.0f / 100) + "%");
            pbProgress.setMax((int) downloadInfo.getTotalLength());
            pbProgress.setProgress((int) downloadInfo.getDownloadLength());
        }

        public void onClick(View v) {
            if (v.getId() == download.getId()) {
                switch (downloadInfo.getState()) {
                    case DownloadManager.PAUSE:
                    case DownloadManager.NONE:
                    case DownloadManager.ERROR:
                        downloadManager.addTask(downloadInfo.getUrl(), downloadInfo.getRequest(), downloadInfo.getListener());
                        break;
                    case DownloadManager.DOWNLOADING:
                        downloadManager.pauseTask(downloadInfo.getUrl());
                        break;
                    case DownloadManager.FINISH:
                        if (ApkUtils.isAvailable(getActivity(), new File(downloadInfo.getTargetPath()))) {
                            ApkUtils.uninstall(getActivity(), ApkUtils.getPackageName(getActivity(), downloadInfo.getTargetPath()));
                        } else {
                            ApkUtils.install(getActivity(), new File(downloadInfo.getTargetPath()));
                        }
                        break;
                }
                refresh();
            } else if (v.getId() == remove.getId()) {
                downloadManager.removeTask(downloadInfo.getUrl());
                adapter.notifyDataSetChanged();
            } else if (v.getId() == restart.getId()) {
                downloadManager.restartTask(downloadInfo.getUrl());
            }
        }
    }
    private class MyDownloadListener extends DownloadListener {
        public void onProgress(DownloadInfo downloadInfo) {
            if (getUserTag() == null) return;
            ViewHolder holder = (ViewHolder) getUserTag();
            holder.refresh();  //这里不能使用传递进来的 DownloadInfo，否者会出现条目错乱的问题
        }
        public void onFinish(DownloadInfo downloadInfo) {
            Toast.makeText(getActivity(), "下载完成:" + downloadInfo.getTargetPath(), Toast.LENGTH_SHORT).show();
        }
        public void onError(DownloadInfo downloadInfo, String errorMsg, Exception e) {
            if (errorMsg != null) Toast.makeText(getActivity(), errorMsg, Toast.LENGTH_SHORT).show();
        }
    }


}

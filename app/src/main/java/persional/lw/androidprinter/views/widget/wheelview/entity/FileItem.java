package persional.lw.androidprinter.views.widget.wheelview.entity;

import android.graphics.drawable.Drawable;

/**
 * Created by 陆伟 on 2017/12/16.
 */

public class FileItem  {
    private Drawable icon;
    private String name;
    private String path = "/";
    private long size = 0;
    private boolean isDirectory = false;

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public Drawable getIcon() {
        return icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public boolean isDirectory() {
        return isDirectory;
    }

    public void setDirectory(boolean isDirectory) {
        this.isDirectory = isDirectory;
    }

}

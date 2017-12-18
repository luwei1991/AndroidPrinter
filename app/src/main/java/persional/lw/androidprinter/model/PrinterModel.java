package persional.lw.androidprinter.model;



/**
 * 打印机状态实体类
 * Created by 陆伟 on 2017/12/6.
 */

public class PrinterModel extends BaserModel {
    /**单页/连页*/
    private Integer page;
    /**拷贝能力*/
    private Integer copy;
    /**速度*/
    private Integer speed;
    /**压缩能力*/
    private Integer compress;
    /**联机状态*/
    private Integer connection;
    /**有纸/无纸*/
    private Integer paper;
    /**正常/报警*/
    private Integer status;
    /**特殊模式*/
    private Integer special;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getCopy() {
        return copy;
    }

    public void setCopy(Integer copy) {
        this.copy = copy;
    }

    public Integer getSpeed() {
        return speed;
    }

    public void setSpeed(Integer speed) {
        this.speed = speed;
    }

    public Integer getCompress() {
        return compress;
    }

    public void setCompress(Integer compress) {
        this.compress = compress;
    }

    public Integer getConnection() {
        return connection;
    }

    public void setConnection(Integer connection) {
        this.connection = connection;
    }

    public Integer getPaper() {
        return paper;
    }

    public void setPaper(Integer paper) {
        this.paper = paper;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getSpecial() {
        return special;
    }

    public void setSpecial(Integer special) {
        this.special = special;
    }

    @Override
    public String toString() {
        return "单页/连页："+ getString(page) + ",拷贝能力:"+getString(copy)+",速度:"+getString(speed)
                +",压缩能力:"+getString(compress)+",联机状态:"+getString(connection)+",有纸/无纸:"
                +getString(paper)+",正常/报警:"+getString(status)+",特殊模式："+getString(special);
    }
}

package com.avantport.avp.njpb.bean;

import java.util.List;

/**
 * Created by len on 2017/8/30.
 * 图片的bean
 */

public class AllPicbean extends BaseJson{

    /**
     * code : 40000
     * message : 查询成功
     * results : [{"category":1,"createBy":1,"createByUserName":"admin","createTime":1501668733149,"fileId":1,"fileMd5":"13231231","fileName":"哈士奇","fileSize":66,"fileType":".jpg","fileUrl":"http://192.168.1.116:4078/12213123124.jpg","id":1,"lastUpdateBy":1,"lastUpdateByUserName":"admin","lastUpdateTime":1501668733149,"remark":"haokan","thumbnail":"125463","workOrderId":1},{"category":2,"createBy":15,"createByUserName":"xiaozhoup","createTime":1501668753709,"fileId":2,"fileMd5":"111111","fileName":"金毛","fileSize":666,"fileType":".pg","fileUrl":"http://192.168.1.116:4078/an1b12bsah32j1j4nj543nn1j.jpg","id":2,"lastUpdateBy":16,"lastUpdateByUserName":"xiaozhou","lastUpdateTime":1501668753709,"remark":"buhaokan","thumbnail":"125463","workOrderId":1}]
     * timestamp : 1504086727977
     */
    /**
     * category : 1
     * createBy : 1
     * createByUserName : admin
     * createTime : 1501668733149
     * fileId : 1
     * fileMd5 : 13231231
     * fileName : 哈士奇
     * fileSize : 66
     * fileType : .jpg
     * fileUrl : http://192.168.1.116:4078/12213123124.jpg
     * id : 1
     * lastUpdateBy : 1
     * lastUpdateByUserName : admin
     * lastUpdateTime : 1501668733149
     * remark : haokan
     * thumbnail : 125463
     * workOrderId : 1
     */

    private List<ResultsBean> results;

    public List<ResultsBean> getResults() {
        return results;
    }

    public void setResults(List<ResultsBean> results) {
        this.results = results;
    }

    public static class ResultsBean {
        private Integer category;
        private Integer createBy;
        private String createByUserName;
        private Long createTime;
        private Integer fileId;
        private String fileMd5;
        private String fileName;
        private Integer fileSize;
        private String fileType;
        private String fileUrl;
        private Integer id;
        private Integer lastUpdateBy;
        private String lastUpdateByUserName;
        private Long lastUpdateTime;
        private String remark;
        private String thumbnail;
        private Integer workOrderId;

        public Integer getCategory() {
            return category;
        }

        public void setCategory(Integer category) {
            this.category = category;
        }

        public Integer getCreateBy() {
            return createBy;
        }

        public void setCreateBy(Integer createBy) {
            this.createBy = createBy;
        }

        public String getCreateByUserName() {
            return createByUserName;
        }

        public void setCreateByUserName(String createByUserName) {
            this.createByUserName = createByUserName;
        }

        public Long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(Long createTime) {
            this.createTime = createTime;
        }

        public Integer getFileId() {
            return fileId;
        }

        public void setFileId(Integer fileId) {
            this.fileId = fileId;
        }

        public String getFileMd5() {
            return fileMd5;
        }

        public void setFileMd5(String fileMd5) {
            this.fileMd5 = fileMd5;
        }

        public String getFileName() {
            return fileName;
        }

        public void setFileName(String fileName) {
            this.fileName = fileName;
        }

        public Integer getFileSize() {
            return fileSize;
        }

        public void setFileSize(Integer fileSize) {
            this.fileSize = fileSize;
        }

        public String getFileType() {
            return fileType;
        }

        public void setFileType(String fileType) {
            this.fileType = fileType;
        }

        public String getFileUrl() {
            return fileUrl;
        }

        public void setFileUrl(String fileUrl) {
            this.fileUrl = fileUrl;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getLastUpdateBy() {
            return lastUpdateBy;
        }

        public void setLastUpdateBy(Integer lastUpdateBy) {
            this.lastUpdateBy = lastUpdateBy;
        }

        public String getLastUpdateByUserName() {
            return lastUpdateByUserName;
        }

        public void setLastUpdateByUserName(String lastUpdateByUserName) {
            this.lastUpdateByUserName = lastUpdateByUserName;
        }

        public Long getLastUpdateTime() {
            return lastUpdateTime;
        }

        public void setLastUpdateTime(Long lastUpdateTime) {
            this.lastUpdateTime = lastUpdateTime;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getThumbnail() {
            return thumbnail;
        }

        public void setThumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
        }

        public Integer getWorkOrderId() {
            return workOrderId;
        }

        public void setWorkOrderId(Integer workOrderId) {
            this.workOrderId = workOrderId;
        }
    }
}

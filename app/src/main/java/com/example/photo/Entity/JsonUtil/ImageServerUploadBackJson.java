package com.example.photo.Entity.JsonUtil;

public class ImageServerUploadBackJson {

    private Boolean status;
    private String message;
    private DataDTO data;

    public static class DataDTO {
        private String key;
        private String name;
        private String pathname;
        private String originName;
        private String size;
        private String mimetype;
        private String extension;
        private String md5;
        private String sha1;
        private DataDTO.LinksDTO links;

        public DataDTO() {
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPathname() {
            return pathname;
        }

        public void setPathname(String pathname) {
            this.pathname = pathname;
        }

        public String getOriginName() {
            return originName;
        }

        public void setOriginName(String originName) {
            this.originName = originName;
        }

        public String getSize() {
            return size;
        }

        public void setSize(String size) {
            this.size = size;
        }

        public String getMimetype() {
            return mimetype;
        }

        public void setMimetype(String mimetype) {
            this.mimetype = mimetype;
        }

        public String getExtension() {
            return extension;
        }

        public void setExtension(String extension) {
            this.extension = extension;
        }

        public String getMd5() {
            return md5;
        }

        public void setMd5(String md5) {
            this.md5 = md5;
        }

        public String getSha1() {
            return sha1;
        }

        public void setSha1(String sha1) {
            this.sha1 = sha1;
        }

        public LinksDTO getLinks() {
            return links;
        }

        public void setLinks(LinksDTO links) {
            this.links = links;
        }

        public static class LinksDTO {
            private String url;
            private String html;
            private String bbcode;
            private String markdown;
            private String markdownWithLink;
            private String thumbnailUrl;
            private String deleteUrl;

            public LinksDTO() {
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getHtml() {
                return html;
            }

            public void setHtml(String html) {
                this.html = html;
            }

            public String getBbcode() {
                return bbcode;
            }

            public void setBbcode(String bbcode) {
                this.bbcode = bbcode;
            }

            public String getMarkdown() {
                return markdown;
            }

            public void setMarkdown(String markdown) {
                this.markdown = markdown;
            }

            public String getMarkdownWithLink() {
                return markdownWithLink;
            }

            public void setMarkdownWithLink(String markdownWithLink) {
                this.markdownWithLink = markdownWithLink;
            }

            public String getThumbnailUrl() {
                return thumbnailUrl;
            }

            public void setThumbnailUrl(String thumbnailUrl) {
                this.thumbnailUrl = thumbnailUrl;
            }

            public String getDeleteUrl() {
                return deleteUrl;
            }

            public void setDeleteUrl(String deleteUrl) {
                this.deleteUrl = deleteUrl;
            }
        }
    }

    public ImageServerUploadBackJson() {
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataDTO getData() {
        return data;
    }

    public void setData(DataDTO data) {
        this.data = data;
    }
}

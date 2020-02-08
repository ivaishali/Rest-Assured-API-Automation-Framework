package com.automation.Data;

public class ContentTypeEnum {
    public enum ContentTypes {
        APPLICATION_VID_JSON("application/vnd.api+json"), APPLICATION_JSON("application/json");

        private String contentType;

        public String getContentTypeString() {
            return this.contentType;
        }

        ContentTypes(String content) {
            this.contentType = content;
        }
    }
}

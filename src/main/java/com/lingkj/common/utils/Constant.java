package com.lingkj.common.utils;

/**
 * 常量
 * 
 * @author admin
 *
 * @date 2016年11月15日 下午1:23:52
 */
public class Constant {
	/** 超级管理员ID */
	public static final int SUPER_ADMIN = 1;

	/**
	 * 菜单类型
	 * 
	 * @author admin
	 *
	 * @date 2016年11月15日 下午1:24:29
	 */
    public enum MenuType {
        /**
         * 目录
         */
    	CATALOG(0),
        /**
         * 菜单
         */
        MENU(1),
        /**
         * 按钮
         */
        BUTTON(2);

        private int value;

        MenuType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }
    
    /**
     * 定时任务状态
     * 
     * @author admin
     *
     * @date 2016年12月3日 上午12:07:22
     */
    public enum ScheduleStatus {
        /**
         * 正常
         */
    	NORMAL(0),
        /**
         * 暂停
         */
    	PAUSE(1);

        private int value;

        ScheduleStatus(int value) {
            this.value = value;
        }
        
        public int getValue() {
            return value;
        }
    }

    /**
     * 云服务商
     */
    public enum CloudService {
        /**
         * 七牛云
         */
        QINIU(1),
        /**
         * 阿里云
         */
        ALIYUN(2),
        /**
         * 腾讯云
         */
        QCLOUD(3);

        private int value;

        CloudService(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }
    /**
     * 用户站内信消息状态
     */
    public enum UserMessageReadStatus {
        /**
         * 未读
         */
        UNREAD(0),
        /**
         * 已读
         */
        READ(1);

        private int readStatus;

        UserMessageReadStatus(int readStatus) { this.readStatus = readStatus; }

        public int getReadStatus() {
            return readStatus;
        }
    }

    /**
     * 设计师申请状态
     */
    public enum UserDesignerApplicationStatus {
        /**
         * 提交审核
         */
        WAIT(0),
        /**
         * 审核成功
         */
        SUCCESS(1),
        /**
         * 审核失败
         */
        ERROR(2);

        private int status;

        UserDesignerApplicationStatus(int status) { this.status = status; }

        public int getStatus() { return status; }
    }

    /**
     * 供应商申请状态
     */
    public enum UserSupplierApplicationStatus {
        /**
         * 提交审核
         */
        WAIT(0),
        /**
         * 审核成功
         */
        SUCCESS(1),
        /**
         * 审核失败
         */
        ERROR(2);

        private int status;

        UserSupplierApplicationStatus(int status) { this.status = status; }

        public int getStatus() { return status; }
    }
    /**
     * 用户申请类型
     */
    public enum ApplicationType {
        /**
         * 设计师
         */
        DESIGNER(1),
        /**
         * 供应商
         */
        SUPPLIER(2),
        /**
         * 代理商
         */
        AGENT(3);

        private int type;

        ApplicationType (int type) { this.type = type; }

        public int getType() { return type; }
    }

}

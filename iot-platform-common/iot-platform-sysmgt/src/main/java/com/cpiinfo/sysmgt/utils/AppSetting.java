package com.cpiinfo.sysmgt.utils;

import java.util.List;
import java.util.Map;
/**
 * ϵͳ����
 * 
 */
public class AppSetting
{
	/**
	 * pm360-config.xml���ļ�·��
	 */
	public static String SYSTEM_PROPERTY_PATH = "";
	/**
	 * SQL_Error.config���ļ�·��
	 */
	public static String SQL_ERROR_PATH = "";
	/**
	 * ��־·��
	 */
	public static String JDP_LOG_PATH = "";
	/**
	 * ������
	 */
	public static final String JDP_PROJECTNAME = "super";
	/**
	 * ���ݿ�����
	 */
	public static final String JDP_DB_ORACLE = "Oracle";
	/**
	 * ���ݿ�����
	 */
	public static final String JDP_DB_DB2 = "DB2";
	/**
	 * ���ݿ�����
	 */
	public static final String JDP_DB_SQLSERVER = "SQLSERVER";
	/**
	 * ���ݿ�����
	 */
	public static final String JDP_DB_MYSQL = "MYSQL";
	/**
	 * ������־����
	 */
	public static boolean EAI_MODDEBUGLOG = true;
	/**
	 * executeXML��״̬
	 */
	public static final String JDP_STATE_NEW = "New";
	/**
	 * executeXML��״̬
	 */
	public static final String JDP_STATE_UPDATE = "Update";
	/**
	 * executeXML��״̬
	 */
	public static final String JDP_STATE_DELETE = "Delete";

	/**
	 * ���� 1=��
	 */
	public static final String JDP_IS = "1";
	/**
	 * ���� 2=��
	 */
	public static final String JDP_NO = "2";
	/**
	 * PIP��Կ
	 */
	public static String PIP_DESENCRYPT_KEY = "850B1032922CDCDC";

	/**
	 * ע���ļ�·��
	 */
	public static String EAI_REGISTER_PATH = "";
	/**
	 * ע���ļ���
	 */
	public static String EAI_REGISTER_NAME = "license.txt";
	/**
	 * ʧЧ�������ļ�·��
	 */
	public static String EAI_LICENSE_HR_EXPIRES_PATH = "";
	/**
	 * /** ʧЧ�������ļ���
	 */
	public static String EAI_LICENSE_HR_EXPIRES = "DayExpires.dat";
	/**
	 * ��֤����Ϣ
	 */
	public static String EAI_REGISTER_MSSAGE = "";
	/**
	 * ����·��
	 */
	public static String PROJECT_PATH = "";
	/**
	 * FTP������������
	 */
	public static String FTP_HOST = "";
	/**
	 * FTP�������û���
	 */
	public static String FTP_USER = "";
	/**
	 * FTP����������
	 */
	public static String FTP_PASSWORD = "";
	/**
	 * FTP�������˿�
	 */
	public static String FTP_PORT = "";
	/**
	 * ��������
	 */
	public static String PROJECT_NAME = "";
	/**
	 * �Ự��ʱ�趨
	 */
	public static String SESSIONTIMEOUT = "30";

	/**
	 * ƽ̨���ݿ���
	 */
	public static String JDP_SHAREPOOL_DBNAME = "PLATFORM";
	/**
	 * ҵ��ϵͳ���ݿ���
	 */
	public static String BIZSYS_SHAREPOOL_DBNAME = "";

	/**
	 * ҵ��ϵͳ�ļ���
	 */
	public static List<String> BIZSYS = null;

	/**
	 * �м����ݿ�����
	 */
	public static String SYSDBTYPE = "";

	/**
	 * ���������û���
	 */
	public static Map<String, String> USERTABLEMAP = null;
	
	/**
	 * �������л�����Ϣ
	 */
	public static Map<String, String> SYSCURR = null;

}

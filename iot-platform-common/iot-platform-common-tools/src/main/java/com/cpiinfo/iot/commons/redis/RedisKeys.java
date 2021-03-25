package com.cpiinfo.iot.commons.redis;

/**
 * 这里定义存放在redis缓存的key
 * 可以统一定义
 * @author liwenjie
 * @date 2020-05-11
 */
public class RedisKeys {
//    /**
//     * 系统参数Key
//     */
//    public static String getSysParamsKey(){
//        return "sys:params";
//    }
//
//    /**
//     * 登录验证码Key
//     */
//    public static String getLoginCaptchaKey(String uuid){
//        return "sys:captcha:" + uuid;
//    }
//
    /**
     * 登录用户Key
     */
    public static String getSecurityUserKey(Long id){
        return "sys:exception:user:" + id;
    }

    /**
     * 无人机经纬度缓存key,key为机场编码+航线编码
     */
    public static String getUAVLatAndLonCacheKey(String airId,String routeCode){
        return "uav:lat:lon:" + airId + "_" + routeCode;
    }

    /**
     * 无人机及机场缓存信息key
     */
    public static String getUAVAndAirportCacheKey(String airId){
        return "uav:airport:state:" + airId;
    }


    /**
     * 无人机异常信息缓存信息key
     */
    public static String getUAVExceptionCacheKey(String uavCode){
        return "uav:exception:msg:" + uavCode;
    }

    /**
     * 机场异常信息缓存信息key
     */
    public static String getAirportExceptionCacheKey(String airportCoder){
        return "airport:exception:msg:" + airportCoder;
    }

    /**
     * 获取电缆测温实时温度在redis中的key
     * @return
     *  实时机场数据缓存信息key
     */
    public static String getCableTemperatureRealTimeKey(){
        return "cable:temperature:realtime";
    }


        /**
         * 获取电缆测温头测温集合数据存放在redis中的key
         * @return
         *  实时无人机数据缓存信息key
         */
        public static String getCableTemperatureListKey(){
            return "cable:temperature:list";
    }


    ////////////////////////////////// /////////////////////////////////////
    /**
     	*  实时机场数据缓存信息key
     */
    public static String getAirportRealTimeCacheKey(String airportkey){
        return "airport:key:msg:" + airportkey;
    }
    
    /**
     	*  实时无人机数据缓存信息key
     */
    public static String getUnmannedAerialVehicleCacheKey(String uavkey){
        return "uav:airport:key:" + uavkey;
    }
    
    /**
 	*  机场通知
	 */
	public static String getAirportMsgCacheKey(String uavmsg){
	    return "uav:air:msg:" + uavmsg;
	}
    ///////////////////////////////////////////////////////////////////////////

    /**
     * 无人机状态
     *  存储 boolean 值 true/false
     * @return key
     */
    public static String getUAVTakeOffStatus() {
	    return "uav:air:status";
    }

    /**
     *  无人机起飞点key
     * @return key 值
     */
    public static String getTaskOffPointKey() {
        return "uav:taskOff";
    }

    /**
     *  大屏机库统计key
     * @return key
     */
    public static String getHangarInfoKey() {
        return "uav:hangarInfo";
    }

    /**
     *  机库获取等待复位key
     * @return key
     */
    public static String getHangarDDFWKey() {
        return "uav:hangar:ddfw";
    }

}


package com.cpiinfo.iot.commons.validator.group;

import javax.validation.GroupSequence;

/**
 * 定义校验顺序，如果AddGroup组失败，则UpdateGroup组不会再校验
 *
 * @author liwenjie
 * @date 2020-05-11
 */
@GroupSequence({AddGroup.class, UpdateGroup.class})
public interface Group {

}

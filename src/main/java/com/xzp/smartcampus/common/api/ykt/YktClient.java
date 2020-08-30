package com.xzp.smartcampus.common.api.ykt;

import com.xzp.smartcampus.common.api.ykt.param.YktRecordQueryParam;
import com.xzp.smartcampus.common.api.ykt.vo.TktRecordVo;
import com.xzp.smartcampus.common.api.ykt.vo.YktResultVo;
import com.xzp.smartcampus.common.exception.SipException;
import com.xzp.smartcampus.common.utils.JsonUtils;
import com.xzp.smartcampus.common.vo.PageResult;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

/**
 * 优卡特消费接口
 *
 * @author SGS
 */
@Component
@ConfigurationProperties(prefix = "ykt")
@Data
public class YktClient {

    /**
     * 请求地址
     */
    private String url;

    @Resource
    private RestTemplate restTemplate;


    /**
     * 查询卡的消费或者充值记录
     *
     * @param queryParam 查询条件
     * @return PageResult<TktRecordVo>
     */
    public PageResult<TktRecordVo> getTurnoverRecordList(YktRecordQueryParam queryParam) {
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        HttpEntity<String> formEntity = new HttpEntity<String>(JsonUtils.toString(queryParam), headers);
        YktResultVo resultVo = restTemplate.postForObject(url + "/ActionApi/Consumption/records", formEntity, YktResultVo.class);
        if (!"0".equals(resultVo.getCode())) {
            throw new SipException("请求失败 " + resultVo.getMsg());
        }
        return new PageResult<>(Long.parseLong(String.valueOf(resultVo.getTotalCount())), resultVo.getTotalPage(), this.strToTktRecordVos(resultVo.getData()));
    }

    /**
     * 转换为vo对象
     *
     * @param data 字符串
     * @return List<TktRecordVo>
     */
    private List<TktRecordVo> strToTktRecordVos(String data) {
        if (StringUtils.isBlank(data) || data.length() <= 5) {
            return Collections.emptyList();
        }
        return JsonUtils.toList(data, TktRecordVo.class);
    }

    /**
     * 删除消费卡
     *
     * @param cardNumber 卡号
     */
    public void deleteCardById(String cardNumber) {

    }

    /**
     * 设置额度
     *
     * @param cardNumber 卡号
     * @param quota      额度
     */
    public void setCardQuota(@NotNull(message = "cardNumber 不能为空") String cardNumber, @NotNull(message = "quota 不能为空") BigDecimal quota) {

    }

    /**
     * 更新卡号
     *
     * @param oldCardnumber 老的卡
     * @param newCardnumber 新的卡
     */
    public void updateCardnumber(String oldCardnumber, @NotNull(message = "newCardnumber 不能为空") String newCardnumber) {

    }

    /**
     * 删除卡
     *
     * @param cardNumbers cardNumbers
     */
    public void deleteCardByIds(List<String> cardNumbers) {

    }

    /**
     * 新增卡
     *
     * @param cardNumber cardNumber
     */
    public void addCard(@NotNull(message = "cardNumber 不能为空") String cardNumber) {

    }

    /**
     * 批量新增卡
     *
     * @param cardNumbers cardNumbers
     */
    public void addCardList(List<String> cardNumbers) {

    }
}

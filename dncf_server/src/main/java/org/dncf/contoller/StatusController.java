package org.dncf.contoller;

import com.alibaba.fastjson.JSON;
import org.dncf.context.DNCSContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by LJT on 17-6-6.
 * email: ljt1343@gmail.com
 */
@RestController
public class StatusController {

    @RequestMapping(value = "/status", method = RequestMethod.GET)
    public String getNodeStatus() {
        return JSON.toJSONString(DNCSContext.CLIENT_INFO_MAP);
    }

}

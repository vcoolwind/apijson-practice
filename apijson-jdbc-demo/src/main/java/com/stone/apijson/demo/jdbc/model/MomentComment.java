package com.stone.apijson.demo.jdbc.model;

import apijson.MethodAccess;
import apijson.RequestMethod;
import apijson.framework.BaseModel;
import com.stone.apijson.extend.RequestStructure;

import static apijson.RequestRole.*;

@MethodAccess(
        GET = {OWNER, ADMIN},
        GETS = {OWNER, ADMIN},
        POST = {UNKNOWN, OWNER, ADMIN},
        DELETE = {OWNER, ADMIN}
)
@RequestStructure(method = RequestMethod.POST, tag = "MomentComment", structure = "{\"MUST\": \"momentId,content\", \"UPDATE\": {\"@role\": \"OWNER\"}, \"REFUSE\": \"id\"}")
@RequestStructure(method = RequestMethod.DELETE, tag = "MomentComment", structure = "{\"MUST\": \"id\", \"REFUSE\": \"!\", \"INSERT\": {\"@role\": \"OWNER\"}}")
public class MomentComment extends BaseModel {
    private long momentId;
    private String content;

    public long getMomentId() {
        return momentId;
    }

    public void setMomentId(long momentId) {
        this.momentId = momentId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "MomentComment{" +
                "momentId=" + momentId +
                ", content='" + content + '\'' +
                "} " + super.toString();
    }
}

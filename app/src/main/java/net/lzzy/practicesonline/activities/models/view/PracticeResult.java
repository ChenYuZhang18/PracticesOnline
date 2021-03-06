package net.lzzy.practicesonline.activities.models.view;

import net.lzzy.practicesonline.activities.constants.ApiConstants;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by lzzy_gxy on 2019/5/8.
 * Description:
 */
public class PracticeResult {
    private static final String SPLITTER = ",";
    private List<QuestionResult> results;
    private int id;
    private String info;

    public PracticeResult(List<QuestionResult> results,int apiId,String info){
        this.results=results;
        this.info=info;
        this.id=apiId;
    }

    public List<QuestionResult> getResults() {
        return results;
    }

    public void setResults(List<QuestionResult> results) {
        this.results = results;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    /** 计算分数 **/
    private double getRatio(){
        int rightCount=0;
        for (QuestionResult result:results){
            if (result.isRight()){
                rightCount++;
            }
        }
        return rightCount * 1.0 / results.size();

    }

    /** 返回错误题目序号 **/
    private String getWrongOrders(){
        int i=0;
        String ids="";
        for (QuestionResult result :results){
            i++;
            if (!result.isRight()){
                ids=ids.concat(i + SPLITTER);
            }
        }
        if (ids.endsWith(SPLITTER)){
            ids = ids.substring(0,ids.length()-1);
        }
        return ids;

    }

    public JSONObject toJson() throws JSONException {
        JSONObject json = new JSONObject();
        json.put(ApiConstants.JSON_RESULT_API_ID,id);
        json.put(ApiConstants.JSON_RESULT_INFO,info);
        json.put(ApiConstants.JSON_RESULT_SCORE_RATIO,
                new DecimalFormat("#.00").format(getRatio()));
        json.put(ApiConstants.JSON_RESULT_WRONG_IDS, getWrongOrders());
        return json;
    }
}

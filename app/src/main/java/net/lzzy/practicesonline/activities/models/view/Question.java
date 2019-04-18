package net.lzzy.practicesonline.activities.models.view;

import net.lzzy.practicesonline.activities.models.QuestionType;
import net.lzzy.sqllib.Ignored;
import net.lzzy.sqllib.Sqlitable;
import java.util.List;
import java.util.UUID;

/**
 * Created by lzzy_gxy on 2019/4/16.
 * Description:
 */
public class Question extends BaseEntity implements Sqlitable {
    public static final String COL_PRACTICE_ID = "practiceId";
    private String content;
    private int dbType;
    private String analysis;
    private UUID practiceId;
    @Ignored
    private QuestionType type;
    @Ignored
    private List<Option> options;

    public QuestionType getType() {
        return type;
    }

    public void setType(QuestionType type) {
        this.type = type;
    }

    public List<Option> getOptions() {
        return options;
    }

    public void setOptions(List<Option> options) {
        this.options = options;
        this.options.addAll(options);
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getDbType() {
        return dbType;
    }

    public void setDbType(int dbType) {
        this.dbType = dbType;
        type=QuestionType.getInstance(dbType);
    }

    public String getAnalysis() {
        return analysis;
    }

    public void setAnalysis(String analysis) {
        this.analysis = analysis;
    }

    public UUID getPracticeId() {
        return practiceId;
    }

    public void setPracticeId(UUID practiceId) {
        this.practiceId = practiceId;
    }

    @Override
    public boolean needUpdate() {
        return false;
    }
}
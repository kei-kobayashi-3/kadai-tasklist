package models.validators;

import java.util.ArrayList;
import java.util.List;

import models.Task;

public class TaskValidator {

    //execute validation
    public static List<String>validate(Task t){
        List<String>errors=new ArrayList<String>();

        String content_error=_validateContent(t.getContent());
        if(!content_error.equals("")){
            errors.add(content_error);
        }
        return errors;
    }

    //"content" input check
    private static String _validateContent(String content){
        if(content==null || content.equals("")){
            return "内容を入力してください。";
        }
        return "";
    }

}

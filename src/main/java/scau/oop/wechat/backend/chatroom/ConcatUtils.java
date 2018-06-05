package scau.oop.wechat.backend.chatroom;

/**
 * @author:czfshine
 * @date:2018/5/22 12:07
 */

public class ConcatUtils {

    public static Concat FindConcatByDisplayName(Concat[] concats,String name){
        for(Concat concat:concats){
            if(concat.getDisplayName().equals(name)){
                return concat;
            }
        }
        return null;
    }
    public static Concat FindConcatByUUID(Concat[] concats,String name){
        for(Concat concat:concats){
            if(concat.getUUID().equals(name)){
                return concat;
            }
        }
        return null;
    }

}

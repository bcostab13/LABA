package example.laba.laba;

import com.orm.SugarRecord;

/**
 * Created by Brenda on 22/05/2015.
 */
public class Marca extends SugarRecord<Marca>{
    private int code;

    public Marca(int code) {
        this.code = code;
    }

    public Marca() {

    }

    public int getCode() {
        return code;
    }

    public void setCode(int c){
        code=c;
    }

    @Override
    public String toString() {
        return ""+getCode();
    }
}

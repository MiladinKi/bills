package bills.entities;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum ETotalPayment {ALL_PAY(0), PART_PAY(1), NOT_PAY(2);

private final int code;

ETotalPayment(int code){
    this.code = code;
}

@JsonValue
private int getCode(){
    return code;
}

@JsonCreator
public static ETotalPayment fromCode (int code){
   for(ETotalPayment value : ETotalPayment.values()){
       if(value.code == code){
           return value;
       }
   }
    throw new IllegalArgumentException("Invalid ETotalPayment code: " + code);

}
}

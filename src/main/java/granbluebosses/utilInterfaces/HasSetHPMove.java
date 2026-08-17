package granbluebosses.utilInterfaces;

public interface HasSetHPMove {
    int getSetHPAmount();
    default String getSetHPAmountString(){
        return Integer.toString(this.getSetHPAmount());
    }
}

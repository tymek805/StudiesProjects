package Semestr_2.PageReplacementAlgorithms;

public class Page {
    private final int pageReference;
    private boolean hasSecondChance = true;
    public Page(int pageReference){
        this.pageReference = pageReference;
    }

    public int getPageReference() {return pageReference;}
    public boolean hasSecondChance() {return hasSecondChance;}
    public void setHasSecondChance(boolean hasSecondChance) {this.hasSecondChance = hasSecondChance;}
}

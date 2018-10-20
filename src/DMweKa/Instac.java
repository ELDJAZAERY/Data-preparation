package DMweKa;


import java.util.*;
import java.util.Map.Entry;

//classe faite pour récupérer toutes les valeurs d'un attributs dans une instance
public class Instac {

	private String attribute;
    private Float max = new Float(0),min=new Float(0);
    private ArrayList<Float> lign =new ArrayList<Float>() ;
    private HashMap<Float,Integer > hm = new HashMap<Float,Integer >();

    private ArrayList<String> ligne =new ArrayList<String>() ;
    private HashMap<String,Integer > occu = new HashMap<String, Integer>();


    public Instac() { }
    public Instac(ArrayList<String> ligne,String attribute) {
        this.attribute = attribute;
        this.ligne = ligne;
    }
    public Instac(String attribute, ArrayList<Float> lign) {
        this.attribute = attribute;
        this.lign = lign;
    }


    public String getAttribute() {
        return attribute;
    }

    public Float getMax() {
        return max;
    }

    public Float getMin() {
        return min;
    }

    public ArrayList<Float> getLign() {
        return lign;
    }

    public HashMap<Float, Integer> getHm() {
        return hm;
    }

    public ArrayList<String> getLigne() {
        return ligne;
    }

    public HashMap<String, Integer> getOccu() {
        return occu;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public void setMax(Float max) {
        this.max = max;
    }

    public void setMin(Float min) {
        this.min = min;
    }

    public void setLign(ArrayList<Float> lign) {
        this.lign = lign;
    }

    public void setHm(HashMap<Float, Integer> hm) {
        this.hm = hm;
    }

    public void setLigne(ArrayList<String> ligne) {
        this.ligne = ligne;
    }

    public void setOccu(HashMap<String, Integer> occu) {
        this.occu = occu;
    }

    // public void calculModenumarique(){TODO}
    
    
    public Integer distinctValues(){
        Map<Double, Integer> hmap = new HashMap<Double, Integer>();

        for (int k=0;k<this.lign.size();k++)  {
            double i=lign.get(k);
            Integer count = hmap.get(i);
            hmap.put(i, count != null ? count + 1 : 1);
        }
        return hmap.size();
    }


    public Float CalculMedian(){
        float med=0;
        Collections.sort(this.lign);
        float j=this.lign.size()/2;
        if(this.lign.size()%2==0){
            med=this.lign.get((int)j)+this.lign.get((int)j+1)+med ;
            }
        else{
            med=this.lign.get(Math.round(j));
        }
        return med;
    }
    
    
    public Float CalculQ1(){
        return this.lign.get(Math.round(this.lign.size()/4));
    }
    
    
    public Float CalculQ3(){
        return this.lign.get(Math.round(this.lign.size()*3/4));
    }
    
    public Float CalculMidRange(){
        return (max+min)/2;

    }
    
    
    public Float Mean(){
        float som=0;
        for(int k=0;k<this.lign.size();k++){
            som=som+this.lign.get(k);
        }
        return som /this.lign.size();
    }
    
    
    public Float Max(){
        Collections.sort(this.lign);
        max=this.lign.get(this.lign.size()-1);
        return max;
    }
    
    
    public Float Min(){
        Collections.sort(this.lign);
        min=this.lign.get(0);
        return min;
    }
    
    
    @SuppressWarnings("unchecked")
	public String Mode(){
        String mode="";
        int nbr=0;
        Map<Double, Integer> hmap = new HashMap<Double, Integer>();

        for (int k=0;k<this.lign.size();k++)  {
            double i=lign.get(k);
            Integer count = hmap.get(i);
            hmap.put(i, count != null ? count + 1 : 1);
        }
        Integer max = Collections.max(hmap.values());

        Set<?> set2 = hmap.entrySet();
        Iterator<?> iterator2 = set2.iterator();
        Entry<String, Integer> me2 ;
        while(iterator2.hasNext()) {
        	me2 =  (Entry<String, Integer>) iterator2.next();
           // NumberFormat format=Number.getInstance();
            if(me2.getValue()==max) {
                mode=mode+Math.round(Double.parseDouble(me2.getKey().toString())*1000.00)/1000.00+",";
                nbr=nbr+1;
            }
        }
        if(nbr==2){
            return mode+"(cette série est bimodale)" ;
        }
        else if(nbr>=3){
            return mode+"(cette série est multimodale)" ;
        }

        return mode +"(Unimodale)";
    }

    
    //for nominale attributes
    public Map <String, Integer> Calculfreq(){

        // Step 1: create Map of String-Integer
        Map<String, Integer> mapOfRepeatedWord = new HashMap<String, Integer>();

        for(String word : this.ligne) {

            // Step 4: convert all String into lower case, before comparison
            String tempUCword = word.toLowerCase();

            // Step 5: check whether Map contains particular word, already
            if(mapOfRepeatedWord.containsKey(tempUCword)){

                // Step 6: If contains, increase count value by 1
                mapOfRepeatedWord.put(tempUCword, mapOfRepeatedWord.get(tempUCword) + 1);
            }
            else {

                // Step 7: otherwise, make a new entry
                mapOfRepeatedWord.put(tempUCword, 1);
            }
        }

        // Step 8: print word along with its count
        for(Entry<String, Integer> entry : mapOfRepeatedWord.entrySet()){
            System.out.println(entry.getKey() + "\t\t" + entry.getValue());
        }

        Integer max = Collections.max(mapOfRepeatedWord.values());
        System.out.println("max is"+max);


        return mapOfRepeatedWord;

    }



}

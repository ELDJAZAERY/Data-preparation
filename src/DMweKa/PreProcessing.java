package DMweKa;

import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;
import java.util.ArrayList;
import java.util.TreeMap;


public class PreProcessing {

    // checked !!
    public static Instances preProcessData(Instances data) {
        int nbAttributs = data.numAttributes();
        ArrayList<Instance> instances = new ArrayList<>(data);

        for(Instance inst:instances){
            if(!inst.hasMissingValue()) continue;
            for(int i=0;i<nbAttributs;i++){
                if(inst.isMissing(i)){
                    if(inst.attribute(i).isNumeric()){
                        inst.setValue(i,modeNumForClass(instances,inst.attribute(i),getClassOfInstance(inst)));
                    }else{
                        inst.setValue(i,modeNomForClass(instances,inst.attribute(i),getClassOfInstance(inst)));
                    }
                }
            }
        }
        return data;
    }


    private static String modeNomForClass(ArrayList<Instance> instances , Attribute attribut, String clas){
        /** For count the frequent of each Attribut's value < Nominal and Date > **/
        TreeMap<String,Integer> valsFreq = new TreeMap<>() ;

        String nominalVal ; Integer freq ;

        /** Mode For  Nominal String and Date Type !! **/
        for(Instance inst:instances){

            // ignor this inst if :
            // 1) missing val of att in this instance
            // 2) this instance class dont equals to clas
            if(inst.isMissing(attribut)) continue;
            if(!appartienClss(inst,clas)) continue ;

            nominalVal = inst.stringValue(attribut);
            if(valsFreq.containsKey(nominalVal)){
                freq = valsFreq.get(nominalVal)+1;
                valsFreq.put(nominalVal,freq);
            }else{
                valsFreq.put(nominalVal,1);
            }
        }

        if(valsFreq.size() == 0) return "";
        return valsFreq.firstKey();
    }

    private static Double modeNumForClass(ArrayList<Instance> instances , Attribute attribut, String clas){
        // For count the frequent of each Attribut's value Numeric
        TreeMap<Double,Integer> valsFreq = new TreeMap<>() ;

        Double numVal ; Integer freq ;

        /** Mode For  Numeric Type !! **/
        for(Instance inst:instances){

            // ignor this inst if :
            // 1) missing val of att in this instance
            // 2) this instance class dont equals to clas
            if(inst.isMissing(attribut)) continue;
            if(!appartienClss(inst,clas)) continue ;

            numVal = inst.value(attribut);
            if(valsFreq.containsKey(numVal)){
                freq = valsFreq.get(numVal)+1;
                valsFreq.put(numVal,freq);
            }else{
                valsFreq.put(numVal,1);
            }
        }

        if(valsFreq.size() == 0) return 0.0;
        return valsFreq.firstKey();
    }




    private static String getClassOfInstance(Instance instance){
        String clas = "missing";
        Attribute classAtt = instance.attribute(instance.numAttributes()-1);
        if(classAtt.name().equalsIgnoreCase("class")){
            if(classAtt.isNumeric()) clas = Double.toString(instance.value(classAtt));
            else clas = instance.stringValue(classAtt);
        }
        return clas;
    }

    private static boolean appartienClss(Instance instance , String class2){
        String class1 = getClassOfInstance(instance);

        if( class2 == null || class1.equals("missing") || class2.equals("missing")) return true ;
        return class1.equalsIgnoreCase(class2);
    }

}

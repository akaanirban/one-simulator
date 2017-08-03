package matlab;
import com.mathworks.engine.*;

import java.io.StringWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;

import java.lang.*;
import java.awt.*;
import java.util.Arrays;


public class javaMatlabOptimisation {
    public static void getOptimisedResults(double[] oneRow, MatlabEngine eng) {
        //creates the A or the Phi matrix in the eng matlab environment
        eng.evalAsync("A = [A; "+Arrays.toString(oneRow)+"];");
    }



    public static void main(String[] args) throws Exception{
        MatlabEngine eng = MatlabEngine.startMatlab(); //start matlab engine

        StringWriter writer = new StringWriter(); //writes a new sring
        eng.eval("cd 'D:\\GitHub\\the-one\\anirban\\l1_ls_matlab'"); //change the folder to optimisation solver dir
        double[] x0 = {1.0,0.0,1.0,0.0}; //pass the 1D matrix as double array
        double[] m = eng.feval("simple_example", x0); //return value is stored as double array
        System.out.println("The returned value" + Arrays.toString(m)); //toString converts 1D double array to string
        //System.out.println("The returned value" + Arrays.deepToString(new double[][]{m}));  //deepToString converts multidimensional array to string
        eng.eval("A = [1,2,3;4,5,6]"); //directly execute commands in matlab using eval
        eng.eval("B = [4,5,6;1,2,3]"); //this executes the command B = [4,5,6;1,2,3] in matlab
        double[][] A = eng.getVariable("A"); //gets a variable from matlab environment and stores in A
        System.out.println(Arrays.deepToString(A));

        //lets try to form the Phi matrix
        eng.evalAsync("A = zeros(1,10);");
        for(int i=0; i<10; i++){
            //eng.eval("A = [A; [1,0,1,0,0,0,0,1,1,1]];");
            double[] row = {1,0,1,0,0,0,0,1,1,1};
            getOptimisedResults(row, eng);
        }
        System.out.println(Arrays.deepToString(eng.getVariable("A")));












        //double[][] a = {{2.0 ,4.0, 6.0}, {3.0, 10, 8}};
        //ArrayList dummyList = new ArrayList()
        //dblArray = javaArray('java.lang.Double',1,10);
        //eng.cd(r'"D:\GitHub\the-one\anirban\l1_ls_matlab\simple_example.m"',nargout=0)
        //double[][] a = {{2.0 ,4.0, 6.0}, {5,6,7}};
        //double[][] roots = eng.feval("sqrt", (Object) a);
        //eng.feval("D:\\GitHub\\the-one\\anirban\\l1_ls_matlab\\simple_example.m");
        //System.out.println(roots);








        eng.close();
    }
}

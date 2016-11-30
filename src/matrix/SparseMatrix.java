package matrix;

import java.io.*;
import java.util.*;

public class SparseMatrix extends Matrix {
    HashMap<Integer, Column> matrix;

    public SparseMatrix(String name) {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(new File(name)));
            ArrayList<String> listmatrix = new ArrayList();
            String line;
            while ((line = reader.readLine()) != null) {
                listmatrix.add(line);
            }
            this.row = listmatrix.size();
            this.column = listmatrix.get(0).split(" ").length;
            this.matrix = new HashMap<Integer, Column>();
            for (int i = 0; i < row; i++) {
                String[] tempS = listmatrix.get(i).split(" ");
                Column key = new Column();
                for (int j = 0; j < column; j++) {
                    double temp = Double.parseDouble(tempS[j]);
                    if (temp != 0) {
                        key.put(j, temp);
                        this.matrix.put(i, key);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public SparseMatrix(Map<Integer, Column> matrix, int row, int column) {
        this.row = row;
        this.column = column;
        this.matrix = (HashMap<Integer, Column>)matrix;
    }

    public Matrix mul(Matrix matrix2) {
        if (matrix2 instanceof SparseMatrix) return (this.mul((SparseMatrix) matrix2));
        else return (this.mul((DenseMatrix) matrix2));
    }

    SparseMatrix transponation(SparseMatrix matrix2) {
        HashMap<Integer, Column> temp = new HashMap<Integer, Column>();
        for (HashMap.Entry<Integer, Column> coordinate1 : matrix2.matrix.entrySet()) {
            for (HashMap.Entry<Integer, Double> coordinate2 : coordinate1.getValue().entrySet()){
                Column key;
                Integer keyElements = (Integer) coordinate2.getKey();
                key = temp.get(keyElements);
                if (key == null) {
                    key = new Column();
                }
                key.put(coordinate1.getKey(),coordinate2.getValue());
                temp.put(keyElements,key);
            }
        }
        return new SparseMatrix(temp,matrix2.column,matrix2.row);
    }


    public SparseMatrix mul(SparseMatrix matrix2) {
        SparseMatrix matrix1 = this;
        if (matrix1.column != matrix2.row) throw new RuntimeException("Перемножить нельзя");
        HashMap<Integer, Column> matrix = new HashMap<Integer, Column>();
        SparseMatrix tempmatrix2=transponation(matrix2);
        double temp=0;
        for (HashMap.Entry<Integer, Column> coordinate1 : matrix1.matrix.entrySet()) {
            Column value=new Column();
            for (HashMap.Entry<Integer, Column> coordinate2 : tempmatrix2.matrix.entrySet()){
                for (HashMap.Entry<Integer, Double> coordinate3 : coordinate1.getValue().entrySet())
                    temp+=matrix1.matrix.get(coordinate1.getKey()).get(coordinate3.getKey())*tempmatrix2.matrix.get(coordinate2.getKey()).get(coordinate3.getKey());
                value.put(coordinate2.getKey(),temp);
                temp=0;
            }
            matrix.put(coordinate1.getKey(),value);

        }
        return new SparseMatrix(matrix,matrix1.row,matrix2.column);
    }

    public SparseMatrix mul(DenseMatrix matrix2) {
        SparseMatrix matrix1 = this;
        if (matrix1.column != matrix2.row) throw new RuntimeException("Перемножить нельзя");
        HashMap<Integer, Column> matrix = new HashMap<Integer, Column>();
        DenseMatrix tempmatrix2=matrix2.transponation(matrix2);
        double temp=0;
        boolean bool=false;
        for (HashMap.Entry<Integer, Column> coordinate1 : matrix1.matrix.entrySet()) {
            Column value=new Column();
            for (int j=0;j<matrix2.column;j++){
                for (HashMap.Entry<Integer, Double> coordinate3 : coordinate1.getValue().entrySet())
                    temp+=matrix1.matrix.get(coordinate1.getKey()).get(coordinate3.getKey())*tempmatrix2.matrix[j][coordinate3.getKey()];
                if (temp!=0) {
                    value.put(j, temp);
                    temp=0;
                    bool=true;
                }
            }
            if (bool)
                matrix.put(coordinate1.getKey(),value);
        }
        return new SparseMatrix(matrix,matrix1.row,matrix2.column);
    }

    public void outSparse(BufferedWriter writer) {
        try {
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < column; j++) {
                    if (matrix.get(i).get(j) != null)
                        writer.write(matrix.get(i).get(j) + " ");
                    else writer.write("0.0");
                }
                writer.write("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();

        }

    }

    public double getElement (int i,int j){
        return matrix.get(i).get(j);
    }

}
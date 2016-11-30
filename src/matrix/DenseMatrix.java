package matrix;
import java.io.*;
import java.util.*;

public class DenseMatrix extends Matrix {
    double[][] matrix;

    public DenseMatrix(String name) {
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
            double[][] matrix = new double[row][column];
            for (int i = 0; i < row; i++) {
                String[] temp = listmatrix.get(i).split(" ");
                for (int j = 0; j < column; j++)
                    matrix[i][j] = Double.parseDouble(temp[j]);
            }
            this.matrix = matrix;
            reader.close();
        } catch (IOException e) {}
    }

    public DenseMatrix (int row, int column) {
        this.row = row;
        this.column = column;
        this.matrix = new double[row][column];
    }

    public DenseMatrix transponation(DenseMatrix matrix2) {
        DenseMatrix temp = new DenseMatrix(matrix2.column, matrix2.row);
        for (int j = 0; j < matrix2.row; j++)
            for (int i = 0; i < matrix2.column; i++)
                temp.matrix[i][j]=matrix2.matrix[j][i];
        return temp;
    }

    public Matrix mul(Matrix matrix2) {
        if (matrix2 instanceof DenseMatrix) return (this.mul((DenseMatrix) matrix2));
        else return (this.mul((SparseMatrix) matrix2));
    }

    public DenseMatrix mul(DenseMatrix matrix2) {
        DenseMatrix matrix1 = this;
        DenseMatrix tempmatrix2=matrix2.transponation(matrix2);
        if (matrix1.column != matrix2.row) throw new RuntimeException("Перемножить нельзя");
        DenseMatrix matrix = new DenseMatrix(matrix1.row, matrix2.column);
        for (int i = 0; i < matrix1.row; i++)
            for (int j = 0; j < matrix2.column; j++)
                for (int k = 0; k < matrix1.column; k++)
                    matrix.matrix[i][j]+=matrix1.matrix[i][k]*tempmatrix2.matrix[j][k];
        return matrix;
    }

    public SparseMatrix mul (SparseMatrix matrix2){
        DenseMatrix matrix1 = this;
        if (matrix1.column != matrix2.row) throw new RuntimeException("Перемножить нельзя");
        HashMap<Integer, Column> matrix = new HashMap<Integer, Column>();
        SparseMatrix tempmatrix2=matrix2.transponation(matrix2);
        double temp=0;
        boolean bool=false;
        for (int i=0;i<matrix1.row;i++) {
            Column value=new Column();
            for (HashMap.Entry<Integer,Column> coordinate2: tempmatrix2.matrix.entrySet()){
                for (int k=0;k<matrix1.column;k++)
                    temp+=matrix1.matrix[i][k]*tempmatrix2.matrix.get(coordinate2.getKey()).get(k);
                if (temp!=0) {
                    value.put(coordinate2.getKey(), temp);
                    temp=0;
                    bool=true;
                }
            }
            if (bool)
                matrix.put(i,value);
        }
        return new SparseMatrix(matrix,matrix1.row,matrix2.column);
    }



    public void outDense(BufferedWriter writer) {
        try {
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < column; j++) {
                    writer.write(matrix[i][j] + " ");

                }
                writer.write("\n");
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    public double getElement (int i,int j){
        return matrix[i][j];
    }
}

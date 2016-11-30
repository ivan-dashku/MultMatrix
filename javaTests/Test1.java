import matrix.DenseMatrix;
import matrix.SparseMatrix;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class Test1 {
    @Test
    public void testmulDD(){
        DenseMatrix matrix1 = new DenseMatrix("data/matrix1.txt");
        DenseMatrix matrix2 = new DenseMatrix("data/matrix2.txt");
        DenseMatrix matrix= matrix1.mul(matrix2);
        DenseMatrix matrixresult = new DenseMatrix("data/result.txt");
        assertEquals(matrixresult,matrix);
    }

    @Test
    public void testmulDS(){
        DenseMatrix matrix1 = new DenseMatrix("data/matrix1.txt");
        SparseMatrix matrix2 = new SparseMatrix("data/matrix2.txt");
        SparseMatrix matrix= matrix1.mul(matrix2);
        SparseMatrix matrixresult = new SparseMatrix("data/result.txt");
        assertEquals(matrixresult,matrix);
    }
    @Test
    public void testmulSD(){
        SparseMatrix matrix1 = new SparseMatrix("data/matrix1.txt");
        DenseMatrix matrix2 = new DenseMatrix("data/matrix2.txt");
        SparseMatrix matrix= matrix1.mul(matrix2);
        SparseMatrix matrixresult = new SparseMatrix("data/result.txt");
        assertEquals(matrixresult,matrix);
    }
    @Test
    public void testmulSS(){
        SparseMatrix matrix1 = new SparseMatrix("data/matrix1.txt");
        SparseMatrix matrix2 = new SparseMatrix("data/matrix2.txt");
        SparseMatrix matrix= matrix1.mul(matrix2);
        SparseMatrix matrixresult = new SparseMatrix("data/result.txt");
        assertEquals(matrixresult,matrix);
    }
}

package gui_test;
//import java.math.*;
public class Matrix_test {
	public int tab[][];
	public int[] getrad() {//µÃµ½1-6µÄÂÒĞò
		int arr[] = new int[6];
		int i;
		for(i=0;i<6;i++) {
			boolean fg=true;
			do {
				fg=true;
				int num=(int) (6*Math.random())+1;
				for(int j=0;j<i;j++) {
					if (num==arr[j]) {
						fg=false;
					}
				}
				arr[i]=num;
			} while (!fg);
			
		}
		return arr;
	}
	public void print() {//´òÓ¡¾ØÕó Èôtab¿Õgg
		int i,j;
		for(i=0;i<5;i++) {
			for(j=0;j<5;j++) {
				System.out.print(tab[i][j]);
				System.out.print(' ');
			}
			System.out.print('\n');
		}
	}
	public Matrix_test() {
		int i,j;
		tab = new int[5][5];
		//for(i=0;i<5;i++) for (j=0;j<5;j++) tab[]
		int[] ran;
		ran=getrad();
		tab[0][0]=-1*ran[0];
		tab[0][1]=-1*ran[1];
		tab[0][2]=-1*ran[3];
		tab[1][0]=-1*ran[4];
		tab[1][1]=-1*ran[5];
		tab[2][0]=-1*ran[2];
		
		int[] ran1=getrad();
		tab[3][4]=ran1[0];
		tab[4][4]=ran1[1];
		tab[4][3]=ran1[3];
		tab[4][2]=ran1[4];
		tab[2][4]=ran1[5];
		tab[3][3]=ran1[2];
		// TODO Auto-generated constructor stub
	}
	public Matrix_test(int[][] tab1) {
		tab = new int[5][5];
		//Éî¿½±´
		int i,j;
		for(i=0;i<5;i++) for(j=0;j<5;j++) tab[i][j]=tab1[i][j];
	}

}

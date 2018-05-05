package gui_test;

public class EvaluFc {
	//用于做 静态局面分析
	private int[][] tab;//存放期盼数据
	public EvaluFc(int[][] tab1) {
		// TODO Auto-generated constructor stub
		tab = new int[5][5];
		//深拷贝
		int i,j;
		for(i=0;i<5;i++) for(j=0;j<5;j++) tab[i][j]=tab1[i][j];
		}
	//count 1-6
	public int ct_me() {
		
		int i,j,c=0;
		for(i=0;i<5;i++) 
			for(j=0;j<5;j++) 
				if(tab[i][j]>6)
					return -1;
				else if((tab[i][j]>0)&&(tab[i][j]<7))//count 1-6
					c++;
		return c;
	}
	public int ct_ai() {
		//count -1
		int i,j,c=0;
		for(i=0;i<5;i++) 
			for(j=0;j<5;j++) 
				if(tab[i][j]<-6)
					return -1;
				else if((tab[i][j]<0)&&(tab[i][j]>-7))
					c++;
		return c;
	}
	//查看 cs棋子的位置,没有返回0length
	public int[] checkcs(int cs) {
		int i,j;
		int adrs[]=new int[2];
		for(i=0;i<5;i++) {
			for(j=0;j<5;j++) {
				if(cs==tab[i][j]) {
					adrs[0]=i;
					adrs[1]=j;
					return adrs;
				}
					 
			}
		}
		adrs=new int[0];
		return adrs;
	}
	//转换角度，旋转180~
	public int[][] revert(int[][] tab) {
		int[][] ret =new int[5][5];
		int i,j,temp1,temp2;
		for(i=0;i<5;i++) {
			for(j=0;j<5-i;j++) {
				temp1=-1*tab[i][j];
				temp2=-1*tab[4-i][4-j];
				ret[i][j]=temp2;
				ret[4-i][4-j]=temp1;
			}
		}
		
		return ret;
	}
	
	public int checknearcs(boolean isleft,int cs) {
		//查看特定棋子周围是否有棋子
		if((Math.abs(cs))==0||Math.abs(cs)>6) {
			return 0;
		}
		if (cs>0) {//people
			if (isleft) {
				if(checkcs(cs-1).length>0) {
					return 2;
				}else if(cs==1){//查看1的左边
					return 0;
				}else {
					return checknearcs(isleft, cs-1);
				}
			}else {//看右边
				if(checkcs(cs+1).length>0) {
					return 2;
				}else if(cs==6){//查看1的左边
					return 0;
				}else {
					return checknearcs(isleft, cs+1);
				}
			}
			
			
		}else if (cs<0) {//ai
			if (isleft) {//ai cs 's left
				if(checkcs(cs+1).length>0) {
					return 2;
				}else if(cs==-1){//查看-1的左边
					return 0;
				}else {
					return checknearcs(isleft, cs+1);
				}
			}else {//看右边
				if(checkcs(cs-1).length>0) {
					return 2;
				}else if(cs==-6){//查看1的左边
					return 0;
				}else {
					return checknearcs(isleft, cs-1);
				}
			}
		}
		
		
		
		return 0;
	}
	
	
	public int reflex() {
		int point=1,rex=0;
		int[] adr=new int[2];
		for(;point<7;point++) {
			adr=checkcs(-1*point);
			if(adr.length!=0) {
				//对于该点数 可直接对应一个棋子
				rex+=2;
			}else {
				rex+=checknearcs(false, -1*point)+checknearcs(true, -1*point);
			}
		}
		int rival_rex=0;
		for(point=1;point<7;point++) {
			adr=checkcs(point);
			if(adr.length!=0) {
				//对于该点数 可直接对应一个棋子
				rival_rex+=2;
			}else {
				rival_rex+=checknearcs(false, point)+checknearcs(true, point);
			}
		}
		
		return rex-rival_rex;
	}
	public int Eva(boolean isme,int[][] table ){
		//isme=true ai（负数代表的）的局面评估
		//isme=0 人（正数代表的）的局面评估
		//eg. Eva(true,table)用在判断自己ai走的棋子的分析
		//win =65536 lost = -65535
		
		tab = new int[5][5];
		//深拷贝
		int i,j;
		for(i=0;i<5;i++) for(j=0;j<5;j++) tab[i][j]=table[i][j];
		
		
		int res=0;
		if (table==null)
			return 0;
		if(isme==false) 
			return Eva(true, revert(table));
		
		//==========结局
		//if()
		
		
		int mychess=ct_ai();
		int enemy=ct_me();
		if((mychess==0)||(tab[0][0]>0)) return -65535;
		if((enemy==0)||(tab[4][4]<0)) return 65536;
		
		
		
		res+= 10*(mychess-enemy);
		
		int mywei[]=new int[7];
		int emwei[]=new int[7];
		//==============attack
		int adres[]=new int[2];
		
		//=========my
		//3 (3,1) 4(1,1) 5(0,2)
		//3 =1*12 4=1*12 5=0*12
		//权值即走到（4，4）的最少步数 4-它
		for(i=-6;i<0;i++) {
			adres=checkcs(i);
			if(adres.length==0) mywei[-1*i]=0;
			else if((i==-6)||(i==-1)){
				mywei[-1*i]=10*Math.min((adres[0]), (adres[1]));
			}else mywei[-1*i]=10*Math.min((adres[0]), (adres[1]));
		}
		
		
		for(i=1;i<7;i++) res+=mywei[i];
		
		//==enm
		for(i=1;i<7;i++) {
			adres=checkcs(i);
			if(adres.length==0) mywei[i]=0;
			else if((i==6)||(i==1)){
				emwei[i]=10*Math.min((4-adres[0]), (4-adres[1]));
			}else emwei[i]=10*Math.min((4-adres[0]), (4-adres[1]));
		}
		for(i=1;i<7;i++) res-=emwei[i];
		
		
		
		//===============defense
		//check heart 
		if((tab[0][0]<-1)&&(tab[0][0]>-6))
			res+=48;
		if((tab[0][0]==-1)||(tab[0][0]==-6))
			res+=48;
		//check shell
		if((tab[0][1]<0)&&(tab[0][1]<0)) {
			res+=25;
		}else if((tab[0][1]<0)||(tab[0][1]<0)){
			res+=25;
		}
		
		
		if((tab[4][4]>1)&&(tab[4][4]<6))
			res-=48;
		if((tab[4][4]==-1)||(tab[4][4]==-6))
			res-=48;
		//check shell
		if((tab[4][3]<0)&&(tab[3][4]<0)) {
			res-=25;
		}else if((tab[4][3]<0)||(tab[3][4]<0)){
			res-=25;
		}
		
		
		
		//res-=25;
		
		int maxcs,nxcs;//权重最大的棋子,次子
		for(i=maxcs=1;i<6;i++) {
			if(mywei[maxcs]<mywei[i+1]) {
				maxcs=i+1;
			}
		}
		
		adres=checkcs(-1*maxcs);
		if(adres.length>0) {
			if(adres[0]*adres[1]>0) {//棋子不在边界
				int x=adres[0],y=adres[1];
				int fg=1;
				if(tab[x-1][y-1]==-1||tab[x-1][y-1]==-6) 
					res+=12;
				else if (tab[x-1][y-1]<0) {
					res-=1;
					fg =0;
				}else {
					res+=12;
				}
				if(fg==1) {//斜上有援助
					if(tab[x-1][y]<0||tab[x][y-1]<0) {
						res+=6;
						if((tab[x-1][y]<0)&&(tab[x][y-1]<0))
							res+=2;
					}
					
				}else {
					if(tab[x-1][y]<0||tab[x][y-1]<0) {
						res+=5;
						if((tab[x-1][y]<0)&&(tab[x][y-1]<0))
							res+=15;
					}
					
				}
		
			}else {
					res-=20;
			}
		}
		
		
		
		if(mychess>1) {
			//算第二大
			int v;
			
			for(i=maxcs=nxcs=1;i<7;i++) {
				v=mywei[i];
				if(v>mywei[nxcs]) {
					if(v>=mywei[maxcs]) {
						nxcs=maxcs;//原来最大值变第二大
						maxcs=i;//最大值更新为当前值
					}else {
						nxcs = i;//当前值为第二大
					}
				}
			}
		
			adres=checkcs(-1*nxcs);
			if(adres[0]*adres[1]>0) {//棋子不在边界
				int x=adres[0],y=adres[1];
				int fg=1;
				if(tab[x-1][y-1]==-1||tab[x-1][y-1]==-6) 
					res+=6;
				else if (tab[x-1][y-1]<0) {
					res-=1;
					fg =0;
				}else {
					res+=6;
				}
				if(fg==1) {//斜上有援助
					if(tab[x-1][y]<0||tab[x][y-1]<0) {
						res+=3;
						if((tab[x-1][y]<0)&&(tab[x][y-1]<0))
							res+=1;
					}
					
				}else {
					if(tab[x-1][y]<0||tab[x][y-1]<0) {
						res+=3;
						if((tab[x-1][y]<0)&&(tab[x][y-1]<0))
							res+=8;
					}
					
				}
		
			}else {
					res-=10;
				}	
		
		}
		
		
		
		
		return res;
	}

	
	
	
}





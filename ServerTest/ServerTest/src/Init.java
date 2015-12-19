import java.util.Random;

public class Init {
	
	
	Init(int difficulty){
		setMineValue(difficulty);
	}
	
	public void setMineValue(int difficulty) {
		
		int rowNum = Data.getInstance().getRow(difficulty);
		int colNum = Data.getInstance().getCol(difficulty);
    	Random random = new Random();
    	int[][] mineValue = new int[rowNum+2][colNum+2];
    	int i=0;
    	int r, c;
    	
    	do{
    		r = random.nextInt(rowNum)+1; // r : [1, rowNum]
    		c = random.nextInt(colNum)+1; // c : [1, colNum]
    		if(mineValue[r][c] == 0) {
    			mineValue[r][c] = -1;
        		i++;
    		}
    		else {
    			continue;
    		}
    		    		
    	} while(i<(rowNum*colNum/6));
    	
    	int sum;

    	for(i=1; i<rowNum+1 ; i++) {
    		for(int j=1 ; j<colNum+1 ; j++) {
    			if(mineValue[i][j]==-1) {
    				continue;
    			}
    			sum=0;
    			
    			if(mineValue[i-1][j-1]==-1) {
    				sum++;
    			}
    			if(mineValue[i-1][j]==-1) {
    				sum++;
    			}
    			if(mineValue[i-1][j+1]==-1) {
    				sum++;
    			}
    			if(mineValue[i][j-1]==-1) {
    				sum++;
    			}
    			if(mineValue[i][j+1]==-1) {
    				sum++;
    			}
    			if(mineValue[i+1][j-1]==-1) {
    				sum++;
    			}
    			if(mineValue[i+1][j]==-1) {
    				sum++;
    			}
    			if(mineValue[i+1][j+1]==-1) {
    				sum++;
    			}
    			mineValue[i][j] = sum;
    		}
    	}
    	Data.getInstance().setMineValue(mineValue);
    }
}
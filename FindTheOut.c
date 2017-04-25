#include"FindTheOut.h"
#include<stdio.h>
#include<stdlib.h>

typedef struct path
{
    int x;
    int y;
    int flag;
    struct path *next;
}path;

static int a[15][15];
path* top=NULL;

void push(int x,int y,int flag){
	path *p;
    if((p = (path*)malloc(sizeof(path))) == NULL);
    else{
    	p->x = x;
        p->y = y;
        p->flag = flag;
        p->next = top->next;
        top->next = p;
	}
}

void pop(){
	path *p;
    if(top->next == NULL);
    else{
    	p = top->next;
        top->next = p->next;
        free(p);
	}
}

void send(){
	path *p,*q,*temp,*a;
	FILE *fp=NULL;
	int i,j;
    p = top->next;
    top->next = NULL;
    free(top);
    top=NULL;
    a=p;
    //调换顺序 
    while(p)
    {
        q = p;
        p = p->next;
        q->next = top;
        top = q;
    }
    temp = top;
    a->next=(path*)malloc(sizeof(path));
    a=a->next;
    a->x=14;a->y=14;
    a->flag=0;
    a->next=NULL;
    fp = fopen("Data.txt","w");
    while(temp)
    {
		fprintf(fp,"%d\n%d\n",temp->x,temp->y);
        temp = temp->next;
    }	
    fclose(fp);
}

void find(){
	int x,y;
    while(1)
    {
        x = top->next->x;
        y = top->next->y;
        if(x==14&&y==14) break;
        else if(a[x][y+1]==0&&x<15&&y+1<15)
        {
            push(x,y+1,1);
            a[x][y+1]=2;
        }
        else if(a[x][y-1]==0&&x<15&&y-1<15&&y-1>=0)
        {
            push(x,y-1,2);
            a[x][y-1]=2;
        }
        else if(a[x+1][y]==0&&x+1<15&&y<15)
        {
            push(x+1,y,3);
            a[x+1][y]=2;
        }
        else if(a[x-1][y]==0&&x-1<15&&y<15&&x-1>=0)
        {
            push(x-1,y,4);
            a[x-1][y]=2;
        }
        else
        {
        	pop();
        }
        if(top->next==NULL) break; 
    }
    if(x==14&&y==14) send();
    else{
    	FILE *fp=NULL;
    	fp = fopen("Data.txt","w");
		fprintf(fp,"-1\n");	
		fclose(fp);
	}
}

void init(){
	top=(path*)malloc(sizeof(path));
	top->next=NULL;
	push(0,0,0);
    a[0][0] = 2;    
    find();
}

JNIEXPORT void JNICALL Java_FindTheOut_receive(JNIEnv *env, jclass cl){ 
	FILE *fp=NULL;
	int i,j;
	fp = fopen("Data.txt","r");
	for(i = 0;i<15;i++)
		for(j = 0;j<15;j++)
			fscanf(fp,"%d\n",&a[i][j]);
	fclose(fp);
	init();
}



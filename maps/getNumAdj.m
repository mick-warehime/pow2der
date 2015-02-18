function numAdj = getNumAdj(map,x,y)

dirs = [1 0; -1 0; 0 1; 0 -1];

numAdj = 0;
for j=1:4
    d = dirs(j,:);
    xd = x+d(1);
    yd = y+d(2);
    
    if map(yd,xd)~=0
        numAdj = numAdj+1;
    end

    if numAdj ==2
        break
    end
end



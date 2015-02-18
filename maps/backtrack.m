function [x,y,oldD,pts] = backtrack(map,xo,yo,pts)

% close point
pts(end,3) = 0;
npts = size(pts,1);

% start at n-1 and reverse direction
n = npts-1;

x = pts(n,1);
y = pts(n,2);

checkedAll = 0;
newD = [];
while ~checkedAll
    
    % if point is closed skip it
    if pts(n,3) ==0
        skip = 1;
    else
        
        xo = x;
        yo = y;
        newD = getDir(map,xo,yo,[0 0]);
        
    end
    
    if isempty(newD)
        skip = 1;
    else
        x = xo;
        y = yo;
        oldD = newD;
        return
    end
    
    if skip
        pts(n,3) = 0;
        n = n-1;
        if n<1
            checkedAll = 1;
        else
            x = pts(n,1);
            y = pts(n,2);
        end
        
    end
    
    
    
end

x = [];
y = [];
oldD = [];


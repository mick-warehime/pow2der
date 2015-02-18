function open = testOpen(map,x,y,group)

width = size(map,2);
height = size(map,1);

dirs = [1 0; 0 1; -1 0; 0 -1];

open = 0;
sameGroup = 0;
for j=1:4
    d = dirs(j,:);
    xd = x+d(1);
    yd = y+d(2);
    
    if xd>(width-1) || xd<2 ||yd>(height-1) || yd<2
        continue
    end
    
    if map(yd,xd) == 0        
        continue
    end
    
    if map(yd,xd) == group
        sameGroup = sameGroup+1;
    else
        return
    end
end

if sameGroup<2
    open = 1;
end
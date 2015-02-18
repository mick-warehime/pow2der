function [xo,yo] = openPoint(map)

width = size(map,2);
height = size(map,1);

xo = [];
yo = [];

dirs = [1 0; 0 1; -1 0; 0 -1];
for x=2:2:width
    for y=2:2:height
        if map(y,x)==0
            count = 0;
            for j=1:4
                d = dirs(j,:);
                xd = x+d(1);
                yd = y+d(2);               
                
                if xd>width || xd<1 ||yd>height || yd<1
                    count = count+1;
                    continue
                end
                
                if map(yd,xd) == 0
                    count = count+1;
                    continue
                else
                    break
                end
                
               
                
                
                
            end
            
            if count == 4
                xo = x;
                yo = y;
                return;
            end
        end
    end
end


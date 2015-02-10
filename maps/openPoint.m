function [xo,yo] = openPoint(map)

xo = [];
yo = [];
for x=1:size(map,1)
    for y=1:size(map,2)
        if isOpen(map,x,y,[0 0])
            xo=x;
            yo=y;
            return;
        end
    
    end
end
function map = expandHallways(map)

% this converts hallways with holes into no hole hallways
%      1 0              1 0 
%  1 1 1 0          1 1 1 0
%  1 0 0 0    -->   1 1 1 0
%  1 1 1 0          1 1 1 0 

height = size(map,1);
width = size(map,2);

for x=2:(width-1)
    for y=2:(height-1)
        
        if map(y,x) == 0
            
            if map(y+1,x)==2 && map(y-1,x) == 2
                map(y,x)=2;
            end
            if map(y,x+1)==2 && map(y,x-1) == 2
                map(y,x)=2;
            end
        end
        
    end
end
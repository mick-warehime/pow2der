function open = openAdjacentSpace(map,i,j)

m = size(map,1);
n = size(map,2);

open = 1;
for k = -1:1
    if i+k>0 && i+k<=m
        for l = -1:1
            if j+l>0 && j+l<=n
               
                if openSpace(map,i+k,j+l)~=1
                    open = 0;
                end
                
            end            
        end
    end
end
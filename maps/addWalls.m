function map = addWalls(map,rooms,door)

for rm = rooms'
    x = rm(1); y = rm(2);
    w = rm(3); h = rm(4);
    
    for xj = (x-1):(x+w)
        for yk = (y-1):(y+h)

            if yk==(y-1) || yk == (y+h) || xj==(x-1) || xj==(x+w)
                if map(yk,xj)~=door
                    
                    map(yk,xj) = door+1;
                end
                
            end
            
        end
    end
    
    
    
end
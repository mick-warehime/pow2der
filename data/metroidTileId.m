% determine what the tile location is for the metroid png

sprite = imread('metroidtiles.png');
imshow(sprite)

pos = ginput(1);

pos = round(pos/16)
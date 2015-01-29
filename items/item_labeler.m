void
addpath('AddTextToImage')
filename = 'item_labels.png';
fmt = 'png';

Im = imread(filename, fmt);

% imshow(A)
n = 16;
s = 768;
del = s/n;
imshow(Im)
x = 1:del:s;
y = 1:del:s;
for j=1:n
    for k = 1:n
        cstr = ['(',num2str(k),',',num2str(j),')'];
        dx = 10;
        dy = 10;
        text(x(j)+dx,y(k)+dy,cstr,'fontsize',10,'color','green')
    end
end

export_fig itemsLabeled -transparent -png




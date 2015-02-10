function xn = boundPts(xn,m,n,tol)

if xn(1)<tol
    xn(1) = tol*(1+rand);
end
if xn(2)<tol
    xn(2) = tol*(1+rand);
end
if xn(1)>(m-tol)
    xn(1) = m-tol*(1+rand);
end

if xn(2)>(n-tol)
    xn(2) = n-tol*(1+rand);
end

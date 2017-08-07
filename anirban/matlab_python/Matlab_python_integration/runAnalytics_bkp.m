wholecontent = fileread('data/content.txt');
splitcontent = strsplit(wholecontent, '#');  %split content at each #

rest = fileread('data/tag.txt');
restcontent = strsplit(rest, '#');
l = 0;
actualSignal = dlmread('data/actualSignal.txt')';
%Xsignal = [118.5,0.0,67.5,0.0,0.0,46.5,0.0,0.0,122.5,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,88.5,0.0,0.0,165.5,0.0,0.0,0.0,0.0,110.5,0.0,63.5,0.0,0.0,0.0, 0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,101.5,0.0,0.0,178.5,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0];
parfor fidx = 1:numel(splitcontent)  %iterate over each section
   y = str2num(splitcontent{fidx});
   Phi = sparse(str2num(restcontent{fidx}));
   Y  = y';          % measurements with no noise
   lambda = 10;      % regularization parameter
   rel_tol = 0.01;     % relative target duality gap
   [x,status]=l1_ls(Phi,Y,lambda,rel_tol, true);
   numerator = 0;
   dinominator = 0;
   l = l+ sqrt(sum((actualSignal-x).^2)/ sum(actualSignal.^2));
   %fprintf('The recovered value of X0 is :\n')
   %disp(x)
   %result = x;
   %l = l+1;
 end

fprintf('The error ratio for lambda %f is : %f\n', lambda, l/numel(splitcontent));
  
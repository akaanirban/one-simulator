function x = runAnalytics(contentFile, tagFile, actualFile, sparsity)
  %runs the error measure calculations on the 
  wholecontent = fileread(contentFile); %read content from file
  splitcontent = strsplit(wholecontent, '#');  %split content at each #
  wholetag = fileread(tagFile); %read the tags from file
  splittag = strsplit(wholetag, '#'); %split the tags at each #
  actualSignal = dlmread(actualFile)'; %read the actual signal

  avgerror = 0 %average error 
  %lambdalist = [10, 1, .1, 0.01]

  parfor fidx = 1:numel(splitcontent)  %iterate over each section
     y = str2num(splitcontent{fidx}); %get the y value
     Phi = sparse(str2num(splittag{fidx})); %get sparse Phi matrix
     Y  = y';          % measurements with no noise
     lambda = 10;      % regularization parameter
     rel_tol = 0.01;     % relative target duality gap
     [x,status]=l1_ls(Phi,Y,lambda,rel_tol, true); %call l1_ls code
     %ACCUMULATE AVERAGE ERROR ON ALL RUNS
     avgerror = avgerror+ sqrt(sum((actualSignal-x).^2)/ sum(actualSignal.^2));
  end
  fprintf('The error ratio for sparsity %d and lambda %f is : %f\n',sparsity, lambda, avgerror/numel(splitcontent));
  x = 1;
A Brain has layers

A layer is a 1D array of neurons.
Any interpretation dimensionality is up to you

Connection between 2 layers are defined by a Kernels .

    A Kernel has ptr array defining a patch on the source.
    A Kernel has neurons for each convolution item.
    The n'th neuron takes its inputs (i) using 
      
       input[mod[n]+ptr[i]]
       
    The connection outputs are packed
     
    
    o[k*nn + i]
        
    i=0  - nn
    nn  -> number convolutions (e.g. neurons for a given kernel)
    k=-  - number of kernels. 
    
    

 
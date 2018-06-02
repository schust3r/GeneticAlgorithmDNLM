# Genetic Algorithm for the DNLM Filter

This repository contains the code used for the genetic algorithm described in the paper "_Automatic calibration of the Deceived Non Local Means Filter for improving the segmentation of
cells in fluorescence based microscopy_". The filter aims to reduce noise while enhancing edges, which can prove useful for segmentation.

Using this stochastic approach, we managed to automatically calibrate four parameters for the DNLM filter: the search window anchor size (w), the neighborhood anchor size (w_n), the gaussian strength (sigma_r), and the gain (lambda).

The genetic algorithm has been able to calibrate the filter to produce a Sørensen-Dice coefficient of over 96% for a set of images when compared to their ground truth. 

![alt text](https://github.com/schust3r/GeneticAlgorithmDNLM/blob/master/images/dnlm_filter_cell_segmentation.png "Cell segmentation with the DNLM filter")

## Implementation

This implementation relies on the OpenCV image processing library to manipulate and transform the input images. The filter itself has been mostly ported from Manuel Zumbado's DNLM-IFFT matlab code at https://github.com/manu3193/DNLM-IFFTT/blob/master/FastNLMFilter.m

The implementation takes advantage of integral images and OpenCV's discrete Fourier Transform to improve performance.

## Further development

Further performance improvements can be applied to the DNLM filter implementation to reduce processing time, which would allow to run larger and more comprehensive experiments. A way to achieve this is proposed on the following paper: https://github.com/manu3193/Tesis_Licenciatura_DNLM-P/blob/master/papers/dnlm-ma-p.pdf

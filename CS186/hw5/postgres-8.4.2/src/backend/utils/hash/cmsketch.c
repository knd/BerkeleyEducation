/*****************************************************************************

	 IMPORTANT: You *must* use palloc0 and pfree, not malloc and free, in your
	 implementation.  This will allow your filter to integrate into PostgreSQL.

******************************************************************************/

#include "postgres.h"
#include "utils/cmsketch.h"

/* initialize the count-min sketch for the specified width and depth */
cmsketch* init_sketch(uint32 width, uint32 depth) {
  cmsketch *cm = (cmsketch *) palloc0(sizeof(cmsketch));
  if (cm == NULL) {
    return NULL;
  }
  cm->width = (int) width;
  cm->depth = (int) depth;
  cm->counters = (int *) palloc0(width*depth * sizeof(int));
  if (cm->counters == NULL) {
    pfree(cm);
    return NULL;
  }
  return cm;
}

/* increment 'bits' in each sketch by 1. 
 * 'bits' is an array of indices into each row of the sketch.
 *    Thus, each index is between 0 and 'width', and there are 'depth' of them.
 */
void increment_bits(cmsketch* sketch, uint32 *bits) {
  int i;
  int width = sketch->width;
  int depth = sketch->depth;
  for (i = 0; i < depth; i++) {
    sketch->counters[i*width + (int) bits[i]]++;
  }
}

/* decrement 'bits' in each sketch by 1.
 * 'bits' is an array of indices into each row of the sketch.
 *    Thus, each index is between 0 and 'width', and there are 'depth' of them.
 */
void decrement_bits(cmsketch* sketch, uint32 *bits) {
  int i;
  int width = sketch->width;
  int depth = sketch->depth;
  for (i = 0; i < depth; i++) {
    sketch->counters[i*width + (int) bits[i]]--;
  }
}

/* return the minimum among the indicies pointed to by 'bits'
 * 'bits' is an array of indices into each row of the sketch.
 *    Thus, each index is between 0 and 'width', and there are 'depth' of them.
 */
uint32 estimate(cmsketch* sketch, uint32 *bits) {
  int width = sketch->width;
  int depth = sketch->depth;
  int i, min = sketch->counters[0*width + (int) bits[0]];
  for (i = 1; i < depth; i++) {
    if (sketch->counters[i*width + (int) bits[i]] < min) {
      min = sketch->counters[i*width + (int) bits[i]];
    }
  }

  return (uint32) min;
}

/* set all values in the sketch to zero */
void reset_sketch(cmsketch* sketch) {
  int i;
  int width = sketch->width;
  int depth = sketch->depth;
  for (i = 0; i < width*depth; i++) {
    sketch->counters[i] = 0;
  }
}

/* destroy the sketch, freeing any memory it might be using */
void destroy_sketch(cmsketch* sketch) {
  pfree(sketch->counters);
  pfree(sketch);  
}

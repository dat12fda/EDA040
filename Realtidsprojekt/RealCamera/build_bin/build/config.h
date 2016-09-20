/* config.h.  Generated from config.h.in by configure.  */
/* config.h.in.  Generated from configure.in by autoheader.  */

/* Set to turn on free block coalescing (only for nonmoving GC) */
#define ALLOC_COALESCE 1

/* Define when building with BITS64 */
#define BITS64 0

/* Set to enable cpu logging */
/* #undef CPU_UTILIZATION_LOGGING */

/* Which GC algorithm to use. see gc.h for details */
#define GC_ALGORITHM 4

/* set to use auto-tuning GC */
/* #undef GC_CYCLE_TIME_ADAPTIVE */

/* Set to turn on coalescing of contihous dead objects b/f free (only for
   nonmoving GC) */
/* #undef GC_FREE_CONTIGOUS_HACK */

/* Set to turn on GC finalizers */
#define GC_HAS_FINALIZERS 1

/* Set if threads enabled */
#define GC_HAS_THREADS 1

/* Size of GC heap */
#define GC_HEAPSIZE (1000000)

/* Set to run GC incrementally */
#define GC_IS_INCREMENTAL 1

/* Set to enable GET_THREAD_ROOT optimization */
#define GC_LAZY_GET_THREAD_ROOT 1

/* EXPERIMENTAL */
/* #undef GC_MARK_ROOTS_ATOMIC */

/* set to measure S_h */
/* #undef GC_MEASURE_HEAPSTATE */

/* Set to turn off the debug layer */
#define GC_NODEBUG 1

/* EXPERIMENTAL */
/* #undef GC_NO_GC_LOCKING */

/* set to use only time and no work metric */
/* #undef GC_ONLY_TIME */

/* set to predict C_GC after mark phase */
/* #undef GC_PREDICT_C */

/* Set to turn on GC warnings */
/* #undef GC_PRINT_DEBUG */

/* Set to turn on GC warnings */
/* #undef GC_PRINT_WARNINGS */

/* Set to turn on GC profiling */
/* #undef GC_PROFILING */

/* Thread model. see gc.h for details */
#define GC_THREAD 1

/* Set to reference call ref params */
#define GC__REF_CALL_PARAMS 1

/* Set to turn on exceptions. See exception.h for details */
#define HAS_EXCEPTIONS 2

/* Define when building with HAS_FLOAT */
#define HAS_FLOAT 0

/* Set to turn on reflection */
#define HAS_REFLECTION 1

/* Define when building for AVR */
/* #undef HOST_AVR */

/* Do memory initialization in allocator on allocation (only for nonmoving GC)
   */
/* #undef INIT_ON_ALLOC */

/* Do memory initialization in allocator on free (only for nonmoving GC) */
#define INIT_ON_FREE 1

/* Do memory initialization in GC (only for nonmoving GC) */
/* #undef INIT_ON_GC */

/* Set to turn on source line info */
/* #undef J2C_SOURCELINES */

/* set to enable Java-specific features */
#define JAVA_2_C 1

/* Define when building with LINUX */
#define LINUX 1

/* set to enable logging (only works with ivm threads) */
/* #undef LOGGING */

/* set to enable non-critical memory */
/* #undef LPMEM */

/* set to measure C_GC */
/* #undef MEASURE_CGC */

/* Name of package */
#define PACKAGE "Server"

/* Define to the address where bug reports for this package should be sent. */
#define PACKAGE_BUGREPORT ""

/* Define to the full name of this package. */
#define PACKAGE_NAME ""

/* Define to the full name and version of this package. */
#define PACKAGE_STRING ""

/* Define to the one symbol short name of this package. */
#define PACKAGE_TARNAME ""

/* Define to the home page for this package. */
#define PACKAGE_URL ""

/* Define to the version of this package. */
#define PACKAGE_VERSION ""

/* Set to use powers-of-two block sizes (only for nonmoving GC) */
/* #undef POWER_OF_TWO_ALIGN */

/* Define when building with RTAI */
/* #undef RTAI */

/* Set to turn on heap and pointer sanity checks (only for nonmoving GC) */
/* #undef SANITY_CHECKS */

/* Define to 1 if you have the ANSI C header files. */
#define STDC_HEADERS 1

/* set to enable GC time measurements */
/* #undef TIME_MEASUREMENTS */

/* set to use the time-triggered GC */
/* #undef TIME_METRIC */

/* Version number of package */
#define VERSION "autotools-0.0.1"

/* Define when building with LINUX */
/* #undef VXWORKS */

/* Set to enable AVR external clock */
/* #undef _AVR_EXT_CLOCK */

/* Set to enable AVR external memory */
#define _AVR_EXT_MEM 1

/* Set to enable UART in the AVR */
/* #undef _AVR_UART */

/* Normal Java thread priority */
#define _RT_NORMAL_PR_JAVA (5)

/* RT Scheduling policy */
#define _RT_SCHEDULING_POSIX (2)

/* Define to empty if `const' does not conform to ANSI C. */
/* #undef const */

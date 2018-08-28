#define DEBUG 0
#if DEBUG
    #define confsdk_trace(...) do { \
        printf("[%d %s:%d(%s)]:",time(NULL),__FILE__,__LINE__,__func__);\
        printf(__VA_ARGS__);\
    } while(0)
#else
    #define confsdk_trace(...) ((void)0)
#endif
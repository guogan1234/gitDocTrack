/* simple-get.c */

/* Contributed by Devin Carraway <devin@bluemug.com> in Debian Bug #165101 */

#include <stdio.h>
#include <ghttp.h>
#include <unistd.h>


void bail(char *s)
{
      fputs(s, stderr); fputc('\n', stderr);
      exit(1);
}

void status(ghttp_request *r, char *desc)
{
      ghttp_current_status st;

      st = ghttp_get_status(r);
      fprintf(stderr, "%s: %s [%d/%d]\n",
                  desc,
                  st.proc == ghttp_proc_request ? "request" :
                  st.proc == ghttp_proc_response_hdrs ? "response-headers" :
                  st.proc == ghttp_proc_response ? "response" : "none",
                  st.bytes_read, st.bytes_total);
}

int main(int argc, char **argv)
{
      int bytes = 0;
      ghttp_request *req;
      ghttp_status req_status;

      if (argc < 2) bail("usage: simple-get URI");

      req = ghttp_request_new();
      if (ghttp_set_uri(req,argv[1]) < 0)
            bail("ghttp_set_uri");
      if (ghttp_prepare(req) < 0)
            bail("ghttp_prepare");

      if (ghttp_set_sync(req, ghttp_async) < 0)
            bail("ghttp_set_sync");

      do {
            status(req, "conn0");
            req_status = ghttp_process(req);

            if (req_status == ghttp_error) {
                  fprintf(stderr, "ghttp err: %s\n",
                              ghttp_get_error(req));
                  return 2;
            }

            if (req_status != ghttp_error && ghttp_get_body_len(req) > 0) {
                  bytes += ghttp_get_body_len(req);
                  ghttp_flush_response_buffer(req);
            }
      } while (req_status == ghttp_not_done);

      fprintf(stderr, "conn0 received %d bytes\n", bytes);
      ghttp_clean(req);
      return 0;
}
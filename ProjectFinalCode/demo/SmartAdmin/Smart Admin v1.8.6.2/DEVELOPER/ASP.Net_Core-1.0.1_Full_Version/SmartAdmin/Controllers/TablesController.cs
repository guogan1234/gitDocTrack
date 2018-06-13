#region Using
 
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;

#endregion

namespace SmartAdmin.Controllers
{
    [Authorize]
    public class TablesController : Controller
    {
        // GET: tables/normal
        public ActionResult Normal()
        {
            return View();
        }

        // GET: tables/data-tables
        public ActionResult DataTables()
        {
            return View();
        }

        // GET: tables/jq-grid
        public ActionResult JQGrid()
        {
            return View();
        }
    }
}
function init_svg(parent_ele, svg_ele) {
    var svg = d3.select($(parent_ele)[0].contentDocument).select(svg_ele);
    var width = svg.attr("width"), height = svg.attr("height");
    svg.attr("viewBox", [0, 0, width, height].join(" "))
            .attr("preserveAspectRatio", "xMinYMin meet");
    svg.select("defs").append("filter").attr("id", "alarm-major")
            .append("feColorMatrix").attr("in", "SourceGraphic")
            .attr("type", "matrix").attr("values", 
        [1, 0, 0, 0, 0,
         0, 0.2, 0, 0, 0,
         0, 0, 0.2, 0, 0,
         0, 0, 0, 1, 0].join(" "));

    svg.select("defs").append("filter").attr("id", "alarm-info")
            .append("feColorMatrix").attr("in", "SourceGraphic")
            .attr("type", "matrix").attr("values", 
        [0, 0, 0, 0, 0,
         0, 1, 0, 0, 0,
         0, 0, 0, 0, 0,
         0, 0, 0, 1, 0].join(" "));

    svg.select("defs").append("filter").attr("id", "alarm-minor")
            .append("feColorMatrix").attr("in", "SourceGraphic")
            .attr("type", "matrix").attr("values", 
        [1, 0.1, 0, 0, 0,
         1, 0.1, 0, 0, 0,
         1, 0.1, 0, 0, 0,
         0, 0, 0, 1, 0].join(" "));
    return svg;
}

function set_status(e, status) {
    e.attr("filter", "url(#alarm-" + status + ")");
}

#define deleteByIds(table, ids)
	delete from #(table) where id in (
		#for(id : ids)
			#(for.index > 0 ? "," : "") #(id)
		#end
	)
#end

#sql("deleteByIds")
	#@deleteByIds(table, ids)
#end


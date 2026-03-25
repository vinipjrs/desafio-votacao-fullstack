import { useNavigate } from 'react-router-dom';
import { usePautas } from '../hooks/usePautas';
import { Plus, ChevronRight, Inbox, Clock } from 'lucide-react';

export default function PautasList() {
  const { pautas, loading } = usePautas();
  const navigate = useNavigate();

  if (loading) return (
    <div className="flex flex-col items-center justify-center py-20 space-y-4">
      <div className="w-12 h-12 border-4 border-green-100 border-t-[#008a51] rounded-full animate-spin"></div>
      <p className="text-slate-400 font-medium animate-pulse text-sm uppercase tracking-widest">Colhendo Pautas...</p>
    </div>
  );

  return (
    <div className="space-y-8 animate-in fade-in slide-in-from-bottom-4 duration-700">
      <header className="px-2 pt-10 pb-4 text-center sm:text-left">
        <h1 className="text-4xl font-bold tracking-tight text-slate-800 mb-2">
          Assembléia <span className="text-[#008a51]">Digital</span>
        </h1>
        <p className="text-slate-500 font-medium">Participe das decisões da sua cooperativa.</p>
      </header>
      {pautas.length === 0 ? (
        <div className="glass-card p-12 flex flex-col items-center text-center space-y-4 border-dashed border-2 border-green-100 bg-transparent shadow-none">
          <div className="bg-green-50 p-4 rounded-full text-green-200"><Inbox size={40} /></div>
          <div>
            <h3 className="text-xl font-bold text-slate-700">Nenhuma pauta ativa</h3>
            <p className="text-slate-400 max-w-xs mx-auto">Propõe algo novo para o futuro da nossa comunidade.</p>
          </div>
          <button onClick={() => navigate('/nova-pauta')} className="btn-primary mt-4"><Plus size={20} /> Começar</button>
        </div>
      ) : (
        <div className="grid gap-4">
          {pautas.map(pauta => (
            <button key={pauta.id} onClick={() => navigate(`/pauta/${pauta.id}`)} className="glass-card p-6 flex items-center justify-between group hover:border-[#008a51]/30 transition-all text-left">
              <div className="flex-1">
                <div className="flex items-center gap-3 mb-2">
                  <span className="text-[10px] font-bold text-[#008a51] uppercase tracking-[0.2em] bg-green-50 px-2 py-0.5 rounded">Pauta #{pauta.id}</span>
                  <div className="flex items-center gap-1 text-slate-400"><Clock size={12} /><span className="text-[10px] uppercase">{new Date(pauta.criadoEm).toLocaleDateString()}</span></div>
                </div>
                <h3 className="text-lg font-bold text-slate-800 group-hover:text-[#008a51] mb-1">{pauta.titulo}</h3>
                <p className="text-sm text-slate-500 line-clamp-1">{pauta.descricao}</p>
              </div>
              <div className="ml-4 bg-slate-50 p-3 rounded-2xl group-hover:bg-[#008a51] group-hover:text-white transition-all"><ChevronRight size={20} /></div>
            </button>
          ))}
        </div>
      )}
    </div>
  );
}
